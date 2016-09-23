import java.util.Hashtable;
import java.util.Enumeration;

public class Graph {

    protected Hashtable<String,String> tbl;

    public Graph() {
        this.tbl = new Hashtable<String,String>();
    }

    public void addClass(String name, String parent) {
        this.tbl.put(name, parent);
    }

    public Enumeration<String> getClasses() {
        return this.tbl.keys();
    }

    public boolean hasCycles(String name, Hashtable<String, Boolean> v) {
    	if(tbl.get(name).equals("no_parent") || tbl.get(name) == null){
    		return false;
    	}else if (v.containsKey(name)){
    		return true;
    	}else{
    		v.put(name,true);
    		return hasCycles(tbl.get(name),v);
    	}
    }

    @Override
    public String toString() {
        return this.tbl.toString();
    }

}
