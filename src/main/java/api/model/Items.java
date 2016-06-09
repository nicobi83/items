package api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.joda.time.DateTime;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * Created by matthew on 28.04.16.
 */
public class Items {
    Set<Item> items = new HashSet<>();

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public static class Item {

        String id;
        String name;
        String description;
        String content;
        Date creationDate;
        Date modifiedDate;


        String sValues;
        Map<String, Object> values = new HashMap<>();

        public Item() {
            this.id = UUID.randomUUID().toString();
            this.creationDate = DateTime.now().toDate();
            this.modifiedDate = this.creationDate;
        }

       /* public String getHref()
        {
            return "http://localhost:8080/" + id;
        } */
        public String getId() {
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        //@JsonIgnore
        public Date getCreationDate() {
            return creationDate;
        }

        /*@JsonIgnore
        public String getCreationDate() {
            DateFormat df = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            return df.format(creationDate.getTime());
        }*/

        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        /*@JsonIgnore
        public void setModCreationDate(String creationDate) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            Date temp;
            try {
                temp = df.parse(creationDate);
                setCreationDate(temp);
                return;
            } catch (Exception e) {
            }
            temp = DateTime.now().toDate();
        }*/

        //@JsonIgnore
        public Date getModifiedDate() {
            return modifiedDate;
        }

        /*@JsonIgnore
        public String getModifiedDate() {
            DateFormat df = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            return df.format(modifiedDate.getTime());
        }*/

        public void setModifiedDate(Date modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        /*@JsonIgnore
        public void setModModifiedDate(String modifiedDate) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            Date temp;
            try {
                temp = df.parse(modifiedDate);
                setModifiedDate(temp);
                return;
            } catch (Exception e) {
            }
            temp = DateTime.now().toDate();
        }*/

        public Map<String, Object> getValues() {
            return values;
        }

        public void setValues(Map<String, Object> values) {
            this.values = values;
        }

        @JacksonXmlProperty(isAttribute = true, namespace = "", localName = "href")
        public String getHref() {
            return UriComponentsBuilder.fromUriString("http://localhost:8080/api")
              .pathSegment(this.getId())
              .build()
              .encode()
              .toUriString();
        }

        @JsonIgnore
        public String getSValues() {
            return sValues;
        }

        public void setSValues(String sValues) {
            this.sValues = sValues;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            return id.equals(item.id);

        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return "Item{" +
              "name='" + name + '\'' +
              ", id='" + id + '\'' +
              '}';
        }

    }
}
