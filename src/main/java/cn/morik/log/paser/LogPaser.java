package cn.morik.log.paser;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @ClassName LogPaser
 * @Author Morik
 * @date 2020.09.28 12:31
 */

@Service
@EnableScheduling
public class LogPaser {
    @Scheduled(fixedRate = 30000)  //30s执行一次
    public void downlogs() {
        ExecutorService executor = Executors.newCachedThreadPool();
        EventFactory<DataInfo> eventFactory = new FtpFactory();
        int ringBufferSize = 2;
        Disruptor<DataInfo> disruptor =
                new Disruptor(eventFactory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleEventsWith(new ADPT(), new BDPT(), new CDPT(), new DDPT(), new EDPT());
        disruptor.start();
        RingBuffer<DataInfo> ringBuffer = disruptor.getRingBuffer();
        DownloadFile ftpDownLoding = new DownloadFile(ringBuffer);
        ftpDownLoding.downloading();
        disruptor.shutdown();
        executor.shutdown();
    }
}
