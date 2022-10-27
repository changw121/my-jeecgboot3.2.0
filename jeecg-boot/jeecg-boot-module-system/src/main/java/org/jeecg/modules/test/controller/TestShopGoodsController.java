package org.jeecg.modules.test.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.test.entity.TestShopGoods;
import org.jeecg.modules.test.service.ITestShopGoodsService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 商品清单
 * @Author: jeecg-boot
 * @Date:   2022-10-22
 * @Version: V1.0
 */
@Api(tags="商品清单")
@RestController
@RequestMapping("/test/testShopGoods")
@Slf4j
public class TestShopGoodsController extends JeecgController<TestShopGoods, ITestShopGoodsService> {
	@Autowired
	private ITestShopGoodsService testShopGoodsService;

	/**
	 * 分页列表查询
	 *
	 * @param testShopGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商品清单-分页列表查询")
	@ApiOperation(value="商品清单-分页列表查询", notes="商品清单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestShopGoods>> queryPageList(TestShopGoods testShopGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestShopGoods> queryWrapper = QueryGenerator.initQueryWrapper(testShopGoods, req.getParameterMap());
		Page<TestShopGoods> page = new Page<TestShopGoods>(pageNo, pageSize);
		IPage<TestShopGoods> pageList = testShopGoodsService.page(page, queryWrapper);
		System.out.println("-----------------pageList");
		System.out.println(pageList.getRecords().get(0));
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param testShopGoods
	 * @return
	 */
	@AutoLog(value = "商品清单-添加")
	@ApiOperation(value="商品清单-添加", notes="商品清单-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestShopGoods testShopGoods) {
		testShopGoodsService.save(testShopGoods);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param testShopGoods
	 * @return
	 */
	@AutoLog(value = "商品清单-编辑")
	@ApiOperation(value="商品清单-编辑", notes="商品清单-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestShopGoods testShopGoods) {
		testShopGoodsService.updateById(testShopGoods);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品清单-通过id删除")
	@ApiOperation(value="商品清单-通过id删除", notes="商品清单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testShopGoodsService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品清单-批量删除")
	@ApiOperation(value="商品清单-批量删除", notes="商品清单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testShopGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品清单-通过id查询")
	@ApiOperation(value="商品清单-通过id查询", notes="商品清单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestShopGoods> queryById(@RequestParam(name="id",required=true) String id) {
		TestShopGoods testShopGoods = testShopGoodsService.getById(id);
		if(testShopGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testShopGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testShopGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestShopGoods testShopGoods) {
        return super.exportXls(request, testShopGoods, TestShopGoods.class, "商品清单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestShopGoods.class);
    }

}
