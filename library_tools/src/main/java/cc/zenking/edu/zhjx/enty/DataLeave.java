package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;

public class DataLeave implements Serializable {

    public String id;
    public String createTime;
    public String confirmTime;
    public String status;
    public String description;
    public String actTime;
    public Partake partake;
    public boolean adminable;
    public String picUrl;
    public Date[] times;
    public String casflag;
    public String actionFile;
    public String lastOutTime;
public String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public Partake getPartake() {
        return partake;
    }

    public void setPartake(Partake partake) {
        this.partake = partake;
    }

    public boolean isAdminable() {
        return adminable;
    }

    public void setAdminable(boolean adminable) {
        this.adminable = adminable;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Date[] getTimes() {
        return times;
    }

    public void setTimes(Date[] times) {
        this.times = times;
    }

    public String getCasflag() {
        return casflag;
    }

    public void setCasflag(String casflag) {
        this.casflag = casflag;
    }

    public String getActionFile() {
        return actionFile;
    }

    public void setActionFile(String actionFile) {
        this.actionFile = actionFile;
    }

    public String getLastOutTime() {
        return lastOutTime;
    }

    public void setLastOutTime(String lastOutTime) {
        this.lastOutTime = lastOutTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public class Partake implements Serializable {

        public String cls;
        public String student;
        //		public String clsId;
        public int studentId;
        public String pic;
        public String pictureUrl;

        public String getCls() {
            return cls;
        }

        public void setCls(String cls) {
            this.cls = cls;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }
    }

    public Type type;

    public class Type implements Serializable {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int id;
        public String value;

    }
}
