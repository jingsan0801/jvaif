package com.jsan.jvaif.web.controller;

import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.vo.ScyResourceVo;
import com.jsan.jvaif.inf.service.IScyResourceService;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static com.jsan.jvaif.inf.constant.ResultEnum.exception_res_getMenu;

/**
 * <p>
 * 资源表  controller
 * </p>
 *
 * @author wangjc
 * @since 2019-09-01
 */
@CrossOrigin
@RestController
@RequestMapping("/scy/res")
@Api("资源")
public class ScyResourceController extends AbstractController {
    @Autowired
    private IScyUserService scyUserService;

    @Autowired
    private IScyResourceService scyResourceService;

    @ApiOperation("获取菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = PublicConstant.REQUEST_AUTH_HEADER, required = true, value = "auth_token值")})
    @GetMapping(value = "/menu")
    public Result getScyResourceByUserName(HttpServletRequest request) {
        Set<ScyRole> roles = scyUserService.getRoleSetByUserName(getUserName(request));
        if (roles != null && roles.size() > 0) {
            List<ScyResourceVo> resources = scyResourceService.getAllMenuByRole(roles);
            if (resources != null && resources.size() > 0) {
                return ResultUtil.success(resources);
            }
        }
        return ResultUtil.fail(exception_res_getMenu);
    }
}

