package com.example.springforum.mapper;

import com.example.springforum.model.DTO.MessageDTO;
import com.example.springforum.model.Message;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper {
    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    @Select("select count(*)\n" +
            "from t_message\n" +
            "where state = 0\n" +
            "  and deleteState = 0\n" +
            "  and receiveUserId = #{id};")
    Integer getUnreadCount(Long id);

    @Select("select m.*,\n" +
            "       u.avatarUrl,\n" +
            "       u.nickname\n" +
            "from t_message m,\n" +
            "     t_user u\n" +
            "where m.postUserId = u.id\n" +
            "  and m.deleteState = 0\n" +
            "  and m.receiveUserId = #{id}\n" +
            "order by m.createTime desc, m.state desc;")
    List<MessageDTO> getMessageByUserId(Long id);


}