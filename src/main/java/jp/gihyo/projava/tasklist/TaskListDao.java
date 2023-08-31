package jp.gihyo.projava.tasklist;

import jp.gihyo.projava.tasklist.HomeController.TaskItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.jdbcTemplate;
import org.springframework.jdbc.core.namedparm.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparm.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jndi.support.SimpleJndiBeanFactory;
import org.springframework.stereotype.Service;

import java.beans.BeanProperty;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TaskListDao {
    private final jdbcTemplate = jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(TaskItem taskItem){
        SqlParameterSource parm = new BeanPropertySqlParameterSource(taskItem);
        SimplejdbcInsert insert =
                new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("tasklist");
        insert.execute(parm);
    }

    public  List <TaskItem> findAll(){
        String query = "SELECT * FROM tasklist";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String ,Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        (Boolean)row.get("done"))).toList();

        return taskItems;

    }
}