package cn.morik.log.paser;

import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Description
 * @ClassName DownloadFile
 * @Author Morik
 * @date 2020.09.28 11:45
 */
@Slf4j
public class DownloadFile {
    RingBuffer<DataInfo> ringBuffer;

    public DownloadFile(RingBuffer<DataInfo> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void downloading() {
        long sequence = ringBuffer.next();
        try {
            //取出空队列
            DataInfo df = ringBuffer.get(sequence);
            //获取时间队列传递数据
            df.setDatainfo(downlod());
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }

    volatile List<DataInfo.FtpInfo> ftps = initData();

    //ftp下载任务分发
    private Map<String, List<DataInfo.FtpInfo>> downlod() {
        //创建线程池
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(N_CONSUMERS);
        ReentrantLock look = new ReentrantLock();
        try {
            look.lock();
            for (DataInfo.FtpInfo ftp : ftps) {
                executorService.submit(() -> {
                    //ftp具体下载函数
                    try {
                        log.info("部门名称：" + ftp.dartment + ftp.name + "的考勤文件正在下载");
                        Thread.sleep(ftp.delay);
                        ftp.uri = "d://" + ftp.dartment + "/" + ftp.name + ".text";
                        log.info("部门名称：" + ftp.dartment + ftp.name + "的考勤文件已下载完成");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            //等待线程池线程全部执行完成
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("考勤文件总个数：" + ftps.size() + "已下载完成");
            Map<String, List<DataInfo.FtpInfo>> map =
                    ftps.stream().collect(Collectors.groupingBy(DataInfo.FtpInfo::getDartment));
            log.info("按部门分组：" + map.size() + "个部门考勤文件下载完成");
            return map;
        } finally {
            look.unlock();
        }

    }

    //伪造数据
    private List<DataInfo.FtpInfo> initData() {
        List<DataInfo.FtpInfo> ftps = new ArrayList<>();
        DataInfo.FtpInfo f01 = new DataInfo().new FtpInfo();
        f01.name = "张三";
        f01.dartment = "财务部";
        f01.delay = 5689;
        ftps.add(f01);

        DataInfo.FtpInfo f02 = new DataInfo().new FtpInfo();
        f02.name = "李四";
        f02.dartment = "行政部";
        f02.delay = 6583;
        ftps.add(f02);

        DataInfo.FtpInfo f03 = new DataInfo().new FtpInfo();
        f03.name = "王五";
        f03.dartment = "人事部";
        f03.delay = 7689;
        ftps.add(f03);

        DataInfo.FtpInfo f04 = new DataInfo().new FtpInfo();
        f04.name = "李六";
        f04.dartment = "技术部";
        f04.delay = 7889;
        ftps.add(f04);

        DataInfo.FtpInfo f05 = new DataInfo().new FtpInfo();
        f05.name = "李七";
        f05.dartment = "技术部";
        f05.delay = 7882;
        ftps.add(f05);

        DataInfo.FtpInfo f06 = new DataInfo().new FtpInfo();
        f06.name = "李八";
        f06.dartment = "技术部";
        f06.delay = 7882;
        ftps.add(f06);

        DataInfo.FtpInfo f07 = new DataInfo().new FtpInfo();
        f07.name = "李九";
        f07.dartment = "技术部";
        f07.delay = 7182;
        ftps.add(f07);


        DataInfo.FtpInfo f010 = new DataInfo().new FtpInfo();
        f010.name = "赵毅";
        f010.dartment = "技术部";
        f010.delay = 7182;
        ftps.add(f010);

        DataInfo.FtpInfo f011 = new DataInfo().new FtpInfo();
        f011.name = "赵二";
        f011.dartment = "销售部";
        f011.delay = 7582;
        ftps.add(f011);

        DataInfo.FtpInfo f012 = new DataInfo().new FtpInfo();
        f012.name = "赵三";
        f012.dartment = "销售部";
        f012.delay = 7152;
        ftps.add(f012);

        DataInfo.FtpInfo f013 = new DataInfo().new FtpInfo();
        f013.name = "赵四";
        f013.dartment = "销售部";
        f013.delay = 7142;
        ftps.add(f013);

        DataInfo.FtpInfo f014 = new DataInfo().new FtpInfo();
        f014.name = "赵五";
        f014.dartment = "销售部";
        f014.delay = 7132;
        ftps.add(f014);

        return ftps;
    }
}
