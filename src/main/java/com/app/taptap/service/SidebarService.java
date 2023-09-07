package com.app.taptap.service;

import com.app.taptap.mapper.CommunityMapper;
import com.app.taptap.mapper.SidebarMapper;
import com.app.taptap.pojo.*;
import idworker.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SidebarService {
    @Resource
    private SidebarMapper sidebarMapper;
    @Resource
    private CommunityMapper communityMapper;
    public List<TagsResponse> sort10ProposedTags(String gameID){
        List<TagsResponse> tags = sidebarMapper.get20TagsWithGameIDByGoodCount(gameID);
        //加权352
        for (TagsResponse tag : tags) {
            tag.setProposedMetrics(0.3 * tag.getGood() +
                    0.5 * tag.getUserCount() + 0.2 * tag.getScanCount());
            User user = communityMapper.getUserInfo_simple(tag.getUserID());
            //补充tag
            tag.setUsername(user.getUsername());
        }
        //按从高到低排序
        tags.sort(new proposedMetricsComparator());
        int cnt = 0;
        List<TagsResponse> result = new ArrayList<>();
        for(TagsResponse tag : tags){
            result.add(tag);
            cnt++;
            if (cnt == 10) break;
        }
        return result;
    }

    //定义比较器
    public class proposedMetricsComparator implements Comparator<TagsResponse> {
        @Override
        public int compare(TagsResponse tag1, TagsResponse tag2) {
            // 根据 rankNum 进行降序排序
            return Double.compare(tag1.getProposedMetrics(), tag2.getProposedMetrics());
        }
    }
}
