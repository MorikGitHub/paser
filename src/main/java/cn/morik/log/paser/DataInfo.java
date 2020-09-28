package cn.morik.log.paser;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName DataInfo
 * @Author Morik
 * @date 2020.09.28 11:24
 */
@Slf4j
@Setter
@Getter
public class DataInfo {
    Map<String, List<FtpInfo>> datainfo;
    @Setter
    @Getter
    class FtpInfo {
        String url;
        String uri;
        String name;
        int delay;
        String dartment;
    }
}
