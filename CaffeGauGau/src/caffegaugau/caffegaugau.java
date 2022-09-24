
package caffegaugau;

public class caffegaugau {
    private String user;
    private String name;
    
    public caffegaugau(){
        user="User";
        name="cafe cafe";
    }
    
    public caffegaugau(String us, String na){
        this.user=us;
        this.name=na;
    }

    public caffegaugau(caffegaugau gaugau){
        this.user=gaugau.user;
        this.name=gaugau.name;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
