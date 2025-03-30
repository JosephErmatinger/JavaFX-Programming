package application;
import java.io.Serializable;
import java.util.List;

public class Configuration implements Serializable {
   
		private static final long serialVersionUID = 1L;
		private List<TvShowElement> tvShowList;

	    public Configuration(List<TvShowElement> tvShowList) {
	        this.tvShowList = tvShowList;
	    }
	    public List<TvShowElement> getTvShowList() {
	        return tvShowList;
	    }
}