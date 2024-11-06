package com.entity.vo;

import com.entity.CaozuorenyuanEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 操作人员
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("caozuorenyuan")
public class CaozuorenyuanVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 账户
     */

    @TableField(value = "username")
    private String username;


    /**
     * 密码
     */

    @TableField(value = "password")
    private String password;


    /**
     * 操作人员姓名
     */

    @TableField(value = "caozuorenyuan_name")
    private String caozuorenyuanName;


    /**
     * 操作人员电话
     */

    @TableField(value = "caozuorenyuan_phone")
    private String caozuorenyuanPhone;


    /**
     * 操作人员身份证号
     */

    @TableField(value = "caozuorenyuan_id_number")
    private String caozuorenyuanIdNumber;


    /**
     * 操作人员头像
     */

    @TableField(value = "caozuorenyuan_photo")
    private String caozuorenyuanPhoto;


    /**
     * 操作人员性别
     */

    @TableField(value = "sex_types")
    private Integer sexTypes;


    /**
     * 逻辑删除
     */

    @TableField(value = "caozuorenyuan_delete")
    private Integer caozuorenyuanDelete;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：账户
	 */
    public String getUsername() {
        return username;
    }


    /**
	 * 获取：账户
	 */

    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * 设置：密码
	 */
    public String getPassword() {
        return password;
    }


    /**
	 * 获取：密码
	 */

    public void setPassword(String password) {
        this.password = password;
    }
    /**
	 * 设置：操作人员姓名
	 */
    public String getCaozuorenyuanName() {
        return caozuorenyuanName;
    }


    /**
	 * 获取：操作人员姓名
	 */

    public void setCaozuorenyuanName(String caozuorenyuanName) {
        this.caozuorenyuanName = caozuorenyuanName;
    }
    /**
	 * 设置：操作人员电话
	 */
    public String getCaozuorenyuanPhone() {
        return caozuorenyuanPhone;
    }


    /**
	 * 获取：操作人员电话
	 */

    public void setCaozuorenyuanPhone(String caozuorenyuanPhone) {
        this.caozuorenyuanPhone = caozuorenyuanPhone;
    }
    /**
	 * 设置：操作人员身份证号
	 */
    public String getCaozuorenyuanIdNumber() {
        return caozuorenyuanIdNumber;
    }


    /**
	 * 获取：操作人员身份证号
	 */

    public void setCaozuorenyuanIdNumber(String caozuorenyuanIdNumber) {
        this.caozuorenyuanIdNumber = caozuorenyuanIdNumber;
    }
    /**
	 * 设置：操作人员头像
	 */
    public String getCaozuorenyuanPhoto() {
        return caozuorenyuanPhoto;
    }


    /**
	 * 获取：操作人员头像
	 */

    public void setCaozuorenyuanPhoto(String caozuorenyuanPhoto) {
        this.caozuorenyuanPhoto = caozuorenyuanPhoto;
    }
    /**
	 * 设置：操作人员性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 获取：操作人员性别
	 */

    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 设置：逻辑删除
	 */
    public Integer getCaozuorenyuanDelete() {
        return caozuorenyuanDelete;
    }


    /**
	 * 获取：逻辑删除
	 */

    public void setCaozuorenyuanDelete(Integer caozuorenyuanDelete) {
        this.caozuorenyuanDelete = caozuorenyuanDelete;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
