package em_clinic_v3;

class Patient {
    private int age, card_number;
    private String id, name, sex, department;

    public Patient(String id, String name, String sex , int age, String department, int card_number) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.department = department;
        this.card_number = card_number;
    }

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCard() {
        return card_number;
    }

    public void setCard(int card_number) {
        this.card_number = card_number;
    }
}
