package com.ilts.anywhere.settings;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SettingsDAOImpl implements SettingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<AppLanguageSerialized> getLanguagesSerialized() {
        List<AppLanguageSerialized> languages = (List<AppLanguageSerialized>) sessionFactory.getCurrentSession()
                .createCriteria(AppLanguageSerialized.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return languages;
    }

    @Override
    @Transactional
    public AppLanguageSerialized getLanguagesSerialized(String id) {
        AppLanguageSerialized language = (AppLanguageSerialized) sessionFactory
                .getCurrentSession()
                .createCriteria(AppLanguageSerialized.class)
                .add(Restrictions.eq("alias", id))
                .list()
                .get(0);
        return language;
    }

    @Override
    @Transactional
    public void updateLanguage(Integer id, String json) {
        AppLanguageSerialized language = (AppLanguageSerialized) sessionFactory
                .getCurrentSession()
                .get(AppLanguageSerialized.class, id);
        language.setJson(json);
        sessionFactory.getCurrentSession()
                .update(language);
    }

    @Override
    @Transactional
    public void createLanguage(String name, String alias, String json) {
        AppLanguageSerialized language = new AppLanguageSerialized();
        language.setName(name);
        language.setAlias(alias);
        language.setJson(json);
        sessionFactory.getCurrentSession()
                .save(language);
    }
}
