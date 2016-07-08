package com.ilts.anywhere.settings;

import java.util.List;
import java.util.Map;

public interface SettingsDAO {
    public List<AppLanguageSerialized> getLanguagesSerialized();
    public AppLanguageSerialized getLanguagesSerialized(String id);
    public void updateLanguage(Integer id, String json);
    public void createLanguage(String name, String alias, String json);
}
