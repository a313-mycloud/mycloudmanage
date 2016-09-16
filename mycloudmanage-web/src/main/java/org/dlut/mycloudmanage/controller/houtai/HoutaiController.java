package org.dlut.mycloudmanage.controller.houtai;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudserver.client.common.vmmanage.MetaData;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

@Controller
public class HoutaiController {
    @Resource(name = "vmBiz")
    private VmBiz vmBiz;
    private static Logger log = LoggerFactory.getLogger(HoutaiController.class);
    @RequestMapping(value = UrlConstant.IMAGE_CAN_DELETE, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String imageCanDelete(HttpServletRequest request, HttpServletResponse response
            , ModelMap model, String hostIp, String imageUuid) {
        JSONObject json = new JSONObject();
        boolean result = this.vmBiz.isCanDelete(hostIp, imageUuid);

        return MyJsonUtils.getJsonString(json, true, true, true, "", result + "");
    }

    @RequestMapping(value = UrlConstant.META_DATA_GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String metadataGet(HttpServletRequest request, HttpServletResponse response
            , ModelMap model) {
        JSONObject json = new JSONObject();
        try {
            String vmLanIp = getIpAddress(request);
            MetaData metadata = this.vmBiz.getMetadataByIp(vmLanIp);
            if (metadata == null)
                return MyJsonUtils.getFailJsonString(json, "");
            return MyJsonUtils.getJsonString(json, true, true, true, "", "" + metadata.getHostName() + "," + metadata.getHostUserName() + "," +
                    metadata.getHostPassword());
        }catch(IOException e){
            e.printStackTrace();
            return MyJsonUtils.getFailJsonString(json, "");
        }
    }
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
    }
}
