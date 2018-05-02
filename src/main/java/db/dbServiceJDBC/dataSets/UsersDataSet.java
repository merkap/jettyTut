package db.dbServiceJDBC.dataSets;

public class UsersDataSet {
    private long id;
    private String name;
    private String pass;
//    private String email;

    public UsersDataSet(long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
