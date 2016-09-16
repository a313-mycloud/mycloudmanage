package org.dlut.mycloudmanage.controller.houtai;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dlut.mycloudserver.client.common.vmmanage.MetaData;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

@Controller
public class HoutaiController {
    @Resource(name = "vmBiz")
    private VmBiz vmBiz;

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
        String vmLanIp=null;
        if (request.getHeader("x-forwarded-for") == null) {
            vmLanIp=request.getRemoteAddr();
        }
        vmLanIp=request.getHeader("x-forwarded-for");
        System.out.println(vmLanIp+"get metadata---");
        JSONObject json = new JSONObject();
        MetaData metadata = this.vmBiz.getMetadataByIp(vmLanIp);
        if (metadata == null)
            return MyJsonUtils.getFailJsonString(json, "");
        return MyJsonUtils.getJsonString(json, true, true, true, "", "" + metadata.getHostName() + "," + metadata.getHostUserName() + "," +
                metadata.getHostPassword());
    }
}
