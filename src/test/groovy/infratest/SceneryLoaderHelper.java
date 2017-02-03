package infratest;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneryLoaderHelper {

    private static List<Scenery> scenarios = Lists.newArrayList();

    private static Map<String, SceneryStrategy> sceneryStrategy = new HashMap<>();

    static {
      sceneryStrategy.put("json", new JsonScenery());
    }

    public static void load(String... files) throws IOException {
        for (String fileName : files) {
            InputStream stream = SceneryLoaderHelper.class.getResourceAsStream("/jsonunit/scenarios" + fileName);
            if (stream == null) {
                throw new IllegalArgumentException("Scenario file [" + fileName +"] not found");
            }
            final String json = IOUtils.toString(stream);
            final String extension = FilenameUtils.getExtension(fileName);
            scenarios.addAll(sceneryStrategy.get(extension).parser(json));
        }

    }

    public static Scenery getRequestScenery(String description) {

        int indexOf = scenarios.indexOf(SceneryLoaderHelper.ExternalEquals.externalEquals(description));

        if (indexOf > -1) {
            return scenarios.get(indexOf);
        }

        throw new IllegalArgumentException("Scenario not found [" + description +"]" + scenarios);
    }

    public static class Scenery {

        private String json;
        private String description;

        Scenery() {
        }

        Scenery(String description) {
            this.description = description;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String toString() {
            return new StringBuilder()
                    .append("JSON: [").append(json).append("] ")
                    .append("DESCRIPTION: [").append(description).append("]").toString();
        }
    }

    private static class ExternalEquals {

        private final Scenery scenery;

        public ExternalEquals(String description) {
            this.scenery = new Scenery(description);
        }

        public static ExternalEquals externalEquals(String description) {
            return new ExternalEquals(description);
        }

        @Override
        public boolean equals(Object e) {
            if (e == null) {
                return Boolean.FALSE;
            }
            if (!(e instanceof Scenery)) {
                return Boolean.FALSE;
            }
            Scenery sceneryParam = (Scenery) e;

            if (sceneryParam.getDescription().equalsIgnoreCase(this.scenery.getDescription())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;
        }
    }

}


