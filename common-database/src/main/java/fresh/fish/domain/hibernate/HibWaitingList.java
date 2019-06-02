package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "waiting_list")
public class HibWaitingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_id")
    private Long waitingId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private HibCustomer hibCustomer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private HibProduct hibProduct;

    @Column(name = "count")
    private Long count;

    @Column(name = "date_create")
    private Timestamp dateCreated;

    @Column(name = "date_close")
    private Timestamp dateClose;

    public HibWaitingList() {
    }

    public HibWaitingList(Long waitingId, HibCustomer hibCustomer, HibProduct hibProduct, Long count, Timestamp dateCreated, Timestamp dateClose) {
        this.waitingId = waitingId;
        this.hibCustomer = hibCustomer;
        this.hibProduct = hibProduct;
        this.count = count;
        this.dateCreated = dateCreated;
        this.dateClose = dateClose;
    }

    public Long getWaitingId() {
        return waitingId;
    }

    public void setWaitingId(Long waitingId) {
        this.waitingId = waitingId;
    }

    public HibCustomer getHibCustomer() {
        return hibCustomer;
    }

    public void setHibCustomer(HibCustomer hibCustomer) {
        this.hibCustomer = hibCustomer;
    }

    public HibProduct getHibProduct() {
        return hibProduct;
    }

    public void setHibProduct(HibProduct hibProductId) {
        this.hibProduct = hibProduct;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateClose() {
        return dateClose;
    }

    public void setDateClose(Timestamp dateClose) {
        this.dateClose = dateClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibWaitingList that = (HibWaitingList) o;
        return Objects.equals(waitingId, that.waitingId) &&
                Objects.equals(hibCustomer, that.hibCustomer) &&
                Objects.equals(hibProduct, that.hibProduct) &&
                Objects.equals(count, that.count) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(dateClose, that.dateClose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitingId, hibCustomer, hibProduct, count, dateCreated, dateClose);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
