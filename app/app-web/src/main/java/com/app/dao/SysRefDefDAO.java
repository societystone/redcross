package com.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.entity.SysRefDef;

/**
 * 系统码值dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRefDefDAO extends BaseDAO<SysRefDef, Long> {

    /**
     * 查询关联数据集合
     * @param relationType
     * @param mainPrimaryId
     * @param refType
     * @return
     */
    List<SysRefDef> selectListByRelation(@Param("relationType")String relationType,@Param("mainPrimaryId")Long mainPrimaryId,@Param("refType")String refType);
}