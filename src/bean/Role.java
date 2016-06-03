package bean;

/**
 * Created by hanpengyu on 2016/4/21.
 */
public class Role {
    private Long rid;
    private String name;
    private String description;
    private Long[] functions;
    private String functionsName;

    public String getFunctionsName() {
        return functionsName;
    }

    public void setFunctionsName(String functionsName) {
        this.functionsName = functionsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long[] getFunctions() {
        return functions;
    }

    public void setFunctions(Long[] functions) {
        this.functions = functions;
    }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
