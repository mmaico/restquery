package infratest;



import java.io.IOException;
import java.util.List;

public interface SceneryStrategy {

  List<SceneryLoaderHelper.Scenery> parser(final String json) throws IOException;
}
