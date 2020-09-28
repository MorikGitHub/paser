package cn.morik.log.paser;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName ADPT
 * @Author Morik
 * @date 2020.09.28 11:30
 */
@Slf4j
public class CDPT implements EventHandler<DataInfo> {

    @Override
    public void onEvent(DataInfo dataInfo, long l, boolean b) throws Exception {
        Map<String, List<DataInfo.FtpInfo>> data = dataInfo.getDatainfo();
        if (data != null && !data.isEmpty()) {
            List<DataInfo.FtpInfo> person = data.get("人事部");
            if (person != null && !person.isEmpty()) {
                for (DataInfo.FtpInfo df : person) {
                    log.info("部门:" + df.dartment + "姓名:" + df.name + "的考勤已解析入库完成");
                }
            }
        }
    }
}
