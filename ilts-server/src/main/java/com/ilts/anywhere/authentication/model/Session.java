package com.ilts.anywhere.authentication.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.persistence.*;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "sessions", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Sessions.findAll", query = "SELECT s FROM Sessions s"),
//    @NamedQuery(name = "Sessions.findBySessionId", query = "SELECT s FROM Sessions s WHERE s.sessionId = :sessionId"),
//    @NamedQuery(name = "Sessions.findByToken", query = "SELECT s FROM Sessions s WHERE s.token = :token"),
//    @NamedQuery(name = "Sessions.findByExpires", query = "SELECT s FROM Sessions s WHERE s.expires = :expires"),
//    @NamedQuery(name = "Sessions.findByDeleted", query = "SELECT s FROM Sessions s WHERE s.deleted = :deleted"),
//    @NamedQuery(name = "Sessions.findByModified", query = "SELECT s FROM Sessions s WHERE s.modified = :modified")})
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Id
//    @Column(name = "session_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_sessions_id")
//    @GenericGenerator( 
//    name="before_insert_sessions_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @org.hibernate.annotations.Parameter( name="keys", value="token" )
//    }
//)
        @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "session_id", unique = true)
    private String sessionId;
   @NaturalId 
    @Column(name = "token", unique = true)
    private String token;
    @Basic(optional = false)
    @Column(name = "expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
    @Basic(optional = false)
    @Column(name = "deleted")
    private Boolean deleted;
    @Basic(optional = false)
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    
 @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = true,referencedColumnName = "user_id")
    private User user;
    public Session() {
    }

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public Session(User user, Boolean deleted, Date modified) {
       
        this.deleted = deleted;
        this.modified = modified;
  
        this.setUser(user);
        this.setExpires(new Date(System.currentTimeMillis() + 3600 * 1000));
        try {
            MessageDigest m = MessageDigest.getInstance("SHA-1");
            m.reset();
            String combined;
            combined = user.getUserId() + this.getExpires();
            m.update(combined.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
            this.setToken(hashtext);
        } catch (Exception e) {
System.out.println("Ise Exception here ******* ??????????????????????"+this.getToken());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilts.anywhere.authentication.model[ sessionId=" + sessionId + " ]";
    }
    
}
