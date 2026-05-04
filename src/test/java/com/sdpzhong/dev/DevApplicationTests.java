package com.sdpzhong.dev;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpzhong.dev.entity.po.Article;
import com.sdpzhong.dev.entity.po.User;
import com.sdpzhong.dev.mapper.ArticleMapper;
import com.sdpzhong.dev.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SpringBootTest
class DevApplicationTests {

    @Resource
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws SQLException {
        System.out.println("获取的数据库连接为:" + dataSource.getConnection());
    }

    @Test
    void deleteUser() {
        int result = userMapper.deleteById("2c4c769d703effd5b94cf6bffe35178d");
        System.out.println(result);
        Assertions.assertEquals(1, result);
    }

    @Test
    void queryUser() {
        User result = userMapper.selectById("2c4c769d703effd5b94cf6bffe35178d");
        System.out.println(result);
        Assertions.assertNull(result);
    }

    /**
     * 批量生成文章数据<br/>
     * 测试方法使用 @Transactional 注解，方法调用结束后会自动回滚，数据不入库
     */
    @Test
    // @Transactional
    void createdArticleData() throws IOException {
        List<Article> articles = new ArrayList<>();
        // String jsonStr = new String(Files.readAllBytes(Paths.get("src/test/java/com/sdpzhong/dev/articles.json")));
        List<Map<String, Object>> jsonList = objectMapper.readValue(new File("src/test/java/com/sdpzhong/dev/articles.json"),
                List.class);

        for (Map<String, Object> json : jsonList) {

            Article article = new Article();

            article.setTitle((String) json.get("title"));
            article.setSubtitle((String) json.get("hot"));
            article.setContent((String) json.get("desc"));
            article.setSubmitTime(new Date());
            article.setUid("fb433e6204e64692ba66c71e9c9e96b2");
            articles.add(article);
        }

        // articles.forEach(System.out::println);
        articles.forEach(articleMapper::insert);

        Assertions.assertEquals(jsonList.size(), articles.size());
    }
}



