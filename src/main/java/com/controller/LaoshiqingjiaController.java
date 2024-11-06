















package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.LaoshiEntity;
import com.entity.LaoshiqingjiaEntity;
import com.entity.view.LaoshiqingjiaView;
import com.service.*;
import com.utils.PageUtils;
import com.utils.PoiUtil;
import com.utils.R;
import com.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * 老师请假管理
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/laoshiqingjia")
public class LaoshiqingjiaController {
    private static final Logger logger = LoggerFactory.getLogger(LaoshiqingjiaController.class);

    @Autowired
    private LaoshiqingjiaService laoshiqingjiaService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private LaoshiService laoshiService;

    @Autowired
    private XueshengService xueshengService;
    @Autowired
    private CaozuorenyuanService caozuorenyuanService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("学生".equals(role))
            params.put("xueshengId",request.getSession().getAttribute("userId"));
        else if("老师".equals(role))
            params.put("laoshiId",request.getSession().getAttribute("userId"));
        else if("操作人员".equals(role))
            params.put("caozuorenyuanId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = laoshiqingjiaService.queryPage(params);

        //字典表数据转换
        List<LaoshiqingjiaView> list =(List<LaoshiqingjiaView>)page.getList();
        for(LaoshiqingjiaView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        LaoshiqingjiaEntity laoshiqingjia = laoshiqingjiaService.selectById(id);
        if(laoshiqingjia !=null){
            //entity转view
            LaoshiqingjiaView view = new LaoshiqingjiaView();
            BeanUtils.copyProperties( laoshiqingjia , view );//把实体数据重构到view中

                //级联表
                LaoshiEntity laoshi = laoshiService.selectById(laoshiqingjia.getLaoshiId());
                if(laoshi != null){
                    BeanUtils.copyProperties( laoshi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setLaoshiId(laoshi.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody LaoshiqingjiaEntity laoshiqingjia, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,laoshiqingjia:{}",this.getClass().getName(),laoshiqingjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("老师".equals(role))
            laoshiqingjia.setLaoshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        Wrapper<LaoshiqingjiaEntity> queryWrapper = new EntityWrapper<LaoshiqingjiaEntity>()
            .eq("laoshi_id", laoshiqingjia.getLaoshiId())
            .eq("qingjia_start_time", laoshiqingjia.getQingjiaStartTime())
            .eq("qingjia_end_time", laoshiqingjia.getQingjiaEndTime())
            .eq("laoshiqingjia_yesno_types", laoshiqingjia.getLaoshiqingjiaYesnoTypes())
            .eq("insert_time", laoshiqingjia.getInsertTime())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        LaoshiqingjiaEntity laoshiqingjiaEntity = laoshiqingjiaService.selectOne(queryWrapper);
//        if(laoshiqingjiaEntity==null){
            laoshiqingjia.setLaoshiqingjiaYesnoTypes(1);
            laoshiqingjia.setInsertTime(new Date());
            laoshiqingjia.setCreateTime(new Date());
            laoshiqingjiaService.insert(laoshiqingjia);
            return R.ok();
//        }else {
//            return R.error(511,"表中有相同数据");
//        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody LaoshiqingjiaEntity laoshiqingjia, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,laoshiqingjia:{}",this.getClass().getName(),laoshiqingjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("老师".equals(role))
            laoshiqingjia.setLaoshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<LaoshiqingjiaEntity> queryWrapper = new EntityWrapper<LaoshiqingjiaEntity>()
            .notIn("id",laoshiqingjia.getId())
            .andNew()
            .eq("laoshi_id", laoshiqingjia.getLaoshiId())
            .eq("qingjia_start_time", laoshiqingjia.getQingjiaStartTime())
            .eq("qingjia_end_time", laoshiqingjia.getQingjiaEndTime())
            .eq("laoshiqingjia_yesno_types", laoshiqingjia.getLaoshiqingjiaYesnoTypes())
            .eq("insert_time", laoshiqingjia.getInsertTime())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        LaoshiqingjiaEntity laoshiqingjiaEntity = laoshiqingjiaService.selectOne(queryWrapper);
//        if(laoshiqingjiaEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      laoshiqingjia.set
            //  }
            laoshiqingjiaService.updateById(laoshiqingjia);//根据id更新
            return R.ok();
//        }else {
//            return R.error(511,"表中有相同数据");
//        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        laoshiqingjiaService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<LaoshiqingjiaEntity> laoshiqingjiaList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            LaoshiqingjiaEntity laoshiqingjiaEntity = new LaoshiqingjiaEntity();
                            laoshiqingjiaEntity.setLaoshiId(Integer.valueOf(data.get(0)));   //老师 要改的
                            laoshiqingjiaEntity.setQingjiaStartTime(new Date(data.get(0)));          //请假开始时间 要改的
                            laoshiqingjiaEntity.setQingjiaEndTime(new Date(data.get(0)));          //请假结束时间 要改的
                            laoshiqingjiaEntity.setQingjiaContent("");//照片
                            laoshiqingjiaEntity.setLaoshiqingjiaYesnoTypes(Integer.valueOf(data.get(0)));   //是否同意 要改的
                            laoshiqingjiaEntity.setInsertTime(date);//时间
                            laoshiqingjiaEntity.setCreateTime(date);//时间
                            laoshiqingjiaList.add(laoshiqingjiaEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        laoshiqingjiaService.insertBatch(laoshiqingjiaList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





}
