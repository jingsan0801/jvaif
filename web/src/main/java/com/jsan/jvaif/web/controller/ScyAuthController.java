package com.jsan.jvaif.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.service.IScyAuthService;
import com.jsan.jvaif.inf.util.PageResultUtil;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.ScyAuthVo;
import com.jsan.jvaif.inf.vo.sys.PageResult;
import com.jsan.jvaif.inf.vo.sys.Result;
import com.jsan.jvaif.web.annotation.OperationLog;
import com.jsan.jvaif.web.annotation.SkipAuthToken;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

import static com.jsan.jvaif.inf.constant.PublicConstant.REQUEST_PARAM_ARRAY_SPLIT;

/**
 * @description: 权限 controller
 * @author: jcwang
 * @create: 2019-09-04 15:48
 **/
@CrossOrigin
@RestController
@Slf4j
@Validated
@Api("auth-权限")
@RequestMapping("/scy/auth")
@ApiModel(value = "Result", description = "通用返回对象")
public class ScyAuthController extends AbstractController {

    @Resource
    private IScyAuthService scyAuthService;

    @SkipAuthToken
    @ApiOperation("新增单个权限")
    @PostMapping(value = "", produces = "application/json")
    public Result addScyAuth(
        @RequestBody
            ScyAuth scyAuth) {
        boolean addFlag = scyAuthService.save(scyAuth);
        return ResultUtil.success(ResultEnum.success_common_add, addFlag);
    }

    @SkipAuthToken
    @ApiOperation("批量新增权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "scyAuthList", value = "权限实体类List", required = true, paramType = "body", dataType = "ScyAuth", allowMultiple = true)})
    @PostMapping(value = "/batch")
    public Result batchAddScyAuth(
        @RequestBody
            List<ScyAuth> scyAuthList) {
        boolean batchAddFlag = scyAuthService.saveBatch(scyAuthList);
        return ResultUtil.success(ResultEnum.success_common_add, batchAddFlag);
    }

    @SkipAuthToken
    @ApiOperation("按id查询权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "scyAuthId", value = "主键id", required = true, paramType = "path", dataType = "String"),
        @ApiImplicitParam(name = "cols", value = "需要返回的列,以,分割", required = false, paramType = "path", dataType = "String")})
    @OperationLog(module = "权限管理", action = "按id查询权限实体", actionDesc = "描述", note = "备注")
    @GetMapping(value = "/{scyAuthId}")
    public Result getScyAuthById(HttpServletRequest request,
        @PathVariable("scyAuthId")
        @NotBlank String scyAuthId) {
        ScyAuth scyAuth = scyAuthService.getById(scyAuthId);
        return ResultUtil.success(scyAuth);
    }

    @SkipAuthToken
    @ApiOperation("按vo查询权限")
    @GetMapping("")
    public Result getScyAuth(HttpServletRequest request, @Valid ScyAuthVo vo) {
        List<ScyAuth> scyAuthList = scyAuthService.getByVo(vo);
        return ResultUtil.success(scyAuthList);
    }

    @SkipAuthToken
    @ApiOperation("按vo查询权限(分页)")
    @GetMapping("/page")
    public PageResult getByVoForPage(HttpServletRequest request, @Valid ScyAuthVo vo) {
        IPage<ScyAuth> scyAuthPage = scyAuthService.getByVoForPage(vo);
        return PageResultUtil.success(scyAuthPage);
    }

    @SkipAuthToken
    @ApiOperation("按id修改权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "scyAuth", value = "权限实体类", required = true, paramType = "body", dataType = "ScyAuth")})
    @PutMapping(value = "")
    public Result updateScyAuthById(
        @RequestBody
            ScyAuth scyAuth) {
        boolean updateFlag = scyAuthService.updateById(scyAuth);
        return ResultUtil.success(ResultEnum.success_common_update, updateFlag);
    }

    @SkipAuthToken
    @ApiOperation("批量修改权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "scyAuthList", value = "权限实体类List", required = true, paramType = "body", dataType = "ScyAuth", allowMultiple = true)})
    @PutMapping(value = "/batch")
    public Result batchUpdateScyAuth(
        @RequestBody
            List<ScyAuth> scyAuthList) {
        boolean updateFlag = scyAuthService.updateBatchById(scyAuthList);
        return ResultUtil.success(ResultEnum.success_common_update, updateFlag);
    }

    @SkipAuthToken
    @ApiOperation("按id删除权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "scyAuthId", value = "权限主键id", required = true, paramType = "path", dataType = "String")})
    @DeleteMapping(value = "/{scyAuthId}")
    public Result delScyAuthById(
        @PathVariable("scyAuthId")
            String scyAuthId) {
        boolean delFlag = scyAuthService.removeById(scyAuthId);
        return ResultUtil.success(ResultEnum.success_common_del, delFlag);
    }

    @SkipAuthToken
    @ApiOperation("按多个id批量删除权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "ids", value = "多个权限主键id,用,分割", required = true, paramType = "query", dataType = "String")})
    @DeleteMapping(value = "/batch")
    public Result batchDelScyAuth(
        @RequestParam("ids")
        @NotBlank String ids) {
        List<String> idList = Arrays.asList(ids.split(REQUEST_PARAM_ARRAY_SPLIT));
        boolean batchDelFlag = scyAuthService.removeByIds(idList);
        return ResultUtil.success(ResultEnum.success_common_del, batchDelFlag);
    }

}
