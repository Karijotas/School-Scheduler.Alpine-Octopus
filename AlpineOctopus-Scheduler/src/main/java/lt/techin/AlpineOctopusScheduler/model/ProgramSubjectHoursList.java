//package lt.techin.AlpineOctopusScheduler.model;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//public class ProgramSubjectHoursList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    private Long pshForCreate_id;
//
//
//
//    public ProgramSubjectHoursList() {
//    }
//
//    public Long getPshForCreate_id() {
//        return pshForCreate_id;
//    }
//
//    public void setPshForCreate_id(Long pshForCreate_id) {
//        this.pshForCreate_id = pshForCreate_id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProgramSubjectHoursList that = (ProgramSubjectHoursList) o;
//        return Objects.equals(id, that.id) && Objects.equals(pshForCreate_id, that.pshForCreate_id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, pshForCreate_id);
//    }
//}
