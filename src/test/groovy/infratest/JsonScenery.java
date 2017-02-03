package infratest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonScenery implements SceneryStrategy {
  
  final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

  @Override
  @SuppressWarnings("all")
  public List<SceneryLoaderHelper.Scenery> parser(final String json) {
    final List<SceneryLoaderHelper.Scenery> scenarios = new ArrayList<>();
    final List<Object> list = JsonPath.parse(json).read("$.[*]", ArrayList.class);
      for (Object object : list) {
        LinkedHashMap<String, String> scenarie = (LinkedHashMap<String, String>) object;
        SceneryLoaderHelper.Scenery scenery = new SceneryLoaderHelper.Scenery();
        scenery.setDescription(scenarie.get("cenario"));
        scenery.setJson(gson.toJson(scenarie.get("json"), LinkedHashMap.class));
        scenarios.add(scenery);
      }
      return scenarios;
    }
    
}
