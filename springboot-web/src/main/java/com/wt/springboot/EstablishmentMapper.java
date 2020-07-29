package com.wt.springboot;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-07-16 下午 2:33
 * introduction
 */
@Mapper
public interface EstablishmentMapper {

    @SelectProvider(type=EstablishmentSqlBuilder.class,method = "buildGetXmglListSql")
    IPage<Map<String, Object>> getXmglList(Page<Map<String, Object>> page, @Param("xmzt") String xmzt, @Param("ghcj") String ghcj, @Param("xmrqMin") Date xmrqMin, @Param("xmrqMax") Date xmrqMax, @Param("other") String other);

    @Update({"<script> UPDATE bzgl_xmxx SET xmgl_guid = #{xmGuid} "
            + "WHERE objectid IN "
            + "<foreach  collection = 'idList' item = 'id' index = 'index' open = '(' separator= ',' close = ')' >"
            + "	#{id} "
            + "</foreach>"
            + "</script>"})
    boolean linkShape(@Param(value = "idList") List<Integer> idList, @Param("xmGuid") String xmGuid);

    //更新关联图形字段
    @Update("update T_BZGL_XMXX set IS_MAP='1' where XMGL_GUID=#{xmGuid}")
    int updateGltx(@Param("xmGuid") String xmGuid);

    @Select("select #{xmglGuid} as xmglGuid, FJML_GUID,FJMLMC,DISPLAY_ORDER,PARENT_ID,XMZT,SFBB,TYPE from T_BZGL_FJML where PARENT_ID='root' and TYPE='bzgl' order by DISPLAY_ORDER")
    @Results(id = "fjmlAndFj",value = {@Result(id = true,column = "FJML_GUID",property = "fjmlGuid"),
              @Result(column = "{PARENT_ID=FJML_GUID,xmglGuid=xmglGuid}",property = "children",
                    many =@Many(select = "getSubFjmlList",fetchType = FetchType.EAGER)),
              @Result(column = "{PARENT_ID=FJML_GUID,xmglGuid=xmglGuid}",property = "fjs",
                    many =@Many(select = "getXmfjList",fetchType = FetchType.EAGER))
    })
    List<TBzglFjml> getFjmlList(@Param("xmglGuid") String xmglGuid);

    @Select("select #{xmglGuid} as xmglGuid,FJML_GUID,FJMLMC,DISPLAY_ORDER,PARENT_ID,XMZT,SFBB,TYPE from T_BZGL_FJML where PARENT_ID=#{PARENT_ID} order by DISPLAY_ORDER")
    @ResultMap("fjmlAndFj")
    List<TBzglFjml> getSubFjmlList(@Param("xmglGuid") String xmglGuid, @Param("PARENT_ID") String parentId);

    @Select("select XMFJ_GUID,FJML_GUID,XMGL_GUID,FJMC,WJLJ,WJDX,SCSJ,SCYH from T_BZGL_XMFJ where XMGL_GUID=#{xmglGuid} and FJML_GUID=#{PARENT_ID}")
    List<TBzglXmfj> getXmfjList(@Param("xmglGuid") String xmglGuid, @Param("PARENT_ID") String parentId);



    @Select("select FJML_GUID,FJMLMC,DISPLAY_ORDER,PARENT_ID ,XMZT,SFBB,TYPE from T_BZGL_FJML where PARENT_ID='root' and TYPE='cghj' order by DISPLAY_ORDER")
    @Results(id = "cghjmls",value = {@Result(id = true,column = "FJML_GUID",property = "fjmlGuid"),
            @Result(column = "FJML_GUID",property = "children",
                    many =@Many(select = "com.gisinfo.sand.establishment.mapper.EstablishmentMapper.getSubCghjmlList",fetchType = FetchType.EAGER)),
    })
    List<TBzglFjml> getCghjmlList(@Param("xmglGuid") String xmglGuid);

    @Select("select FJML_GUID,FJMLMC,DISPLAY_ORDER,PARENT_ID,XMZT,SFBB,TYPE from T_BZGL_FJML where PARENT_ID=#{PARENT_ID} order by DISPLAY_ORDER")
    @ResultMap("cghjmls")
    List<TBzglFjml> getSubCghjmlList(@Param("PARENT_ID") String parentId);


    @Update({"<script> UPDATE temp SET xmgl_guid = #{xmglGuid} , xmfj_guid=#{xmfjGuid}  "
            + "WHERE objectid IN "
            + "<foreach  collection = 'idList' item = 'id' index = 'index' open = '(' separator= ',' close = ')' >"
            + "	#{id} "
            + "</foreach>"
            + "</script>"})
    boolean linkTempShape(@Param(value = "idList") List<String> idList, @Param("xmglGuid") String xmglGuid, @Param("xmfjGuid") String xmfjGuid);

    //根据当前目录找到对应项目状态
    @Select("select xmzt from T_BZGL_FJML where fjml_guid = #{fjmlGuid}")
    Map<String,String> queryCurrentXmzt(@Param("fjmlGuid") String fjmlGuid);

    //根据当前项目状态找到当前在字典表的顺序
    @Select("select display_order from GMAP_DICT where dict_code = #{xmzt}")
    Map<String,Object> queryNextXmztOrder(@Param("xmzt") String currentXmzt);

    //根据顺序找到项目状态
    @Select("select dict_code from GMAP_DICT where dict_type = 'XMZT' and display_order=#{displayOrder}")
    Map<String,String> queryNextXmzt(@Param("displayOrder") Integer displayOrder);

    //更新项目状态信息
    @Update("update T_BZGL_XMXX set xmzt=#{xmzt} where XMGL_GUID=#{xmGuid}")
    int updateXmzt(@Param("xmGuid") String xmGuid, @Param("xmzt") String xmzt);

    class EstablishmentSqlBuilder{

        public static String buildGetXmglListSql(Page<Map<String, Object>> page, @Param("xmzt") String xmzt, @Param("ghcj") String ghcj, @Param("xmrqMin") Date xmrqMin, @Param("xmrqMax") Date xmrqMax, @Param("other") String other){
            StringBuilder sb = new StringBuilder("");
            sb.append("select t.*,t2.dict_name as xmztmc,t3.dict_name as ghcjmc from t_bzgl_xmxx t left join gmap_dict t2 on t2.dict_code=t.xmzt and t2.dict_type='XMZT' left join gmap_dict t3 on t3.dict_code=t.ghcj and t3.dict_type='GHCJ'where 1=1");
            if(!Strings.isNullOrEmpty(xmzt)){
                sb.append(" and t.xmzt=#{xmzt}");
            }
            if(!Strings.isNullOrEmpty(ghcj)){
                sb.append(" and t.ghcj=#{ghcj}");
            }
            if (xmrqMin != null) {
                sb.append(" and t.cjsj>=#{xmrqMin}");
            }
            if (xmrqMax != null) {
                sb.append(" and t.cjsj<=#{xmrqMax}");
            }
            if(!Strings.isNullOrEmpty(other)){
                sb.append(" and (t.xmmc like '%'||#{other}||'%' or t.zzdw like '%'||#{other}||'%' or t.bzdw like '%'||#{other}||'%' )");
            }
            sb.append(" order by cjsj desc");
            return sb.toString();
        }
    }

}
