package A;

public class Inf {
    private String name;
    private long phone;

    public Inf(String name, long phone) {
        this.name = name;
        this.phone = phone;
    }

    public Inf(String data) {
        String[] split = data.split(" ");
        this.name = split[0];
        this.phone = Long.parseLong(split[1]);
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

}
