package cblog.core.persist.dao.impl;

import cblog.annotation.Repository;
import cblog.core.persist.dao.BaseDao;
import org.hibernate.Criteria;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenchicheng on 17-9-20.
 *
 * 持久层基类，通过泛型指定实体类
 * @param <T> 实体类
 */
public class BaseDaoImpl<T> extends  GenericDaoImpl implements BaseDao<T> {


    /**
     * 实体类型
     */
    protected Class<T> entityClass;

    /**
     * 自动提取entityClass
     */
    @PostConstruct
    public void init() throws Exception {
        Repository repository = this.getClass().getAnnotation(Repository.class);
        Assert.notNull(repository, this.getClass() + "必须使用" + Repository.class + "注解！");
        Assert.notNull(repository.entity(), this.getClass() + "的 @Repository注解的 entity 不能为空");

        this.entityClass = (Class<T>) repository.entity();
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @Override
    public T get(Serializable id) {
        return get(entityClass,id);
    }

    @Override
    public void deleteById(Serializable id) {
        deleteById(entityClass, id);
    }

    @Override
    public void deleteAll(Collection<Serializable> ids) {
        Assert.notNull(ids, "ids不能为空");
        for(Serializable id : ids) {
            deleteById(id);
        }
    }

    /**
     *创建Criteria查询对象
     * @return
     */
    protected Criteria createCriteria() {
        return createCriteria(entityClass);
    }

    @Override
    public List<T> list() {
        return createCriteria().list();
    }
}