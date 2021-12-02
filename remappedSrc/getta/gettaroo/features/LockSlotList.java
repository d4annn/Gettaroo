package getta.gettaroo.features;

import java.util.List;

public class LockSlotList {
    private List<LockSlot> list;

    public LockSlotList(List<LockSlot> list) {
        this.list = list;
    }

    public List<LockSlot> getList() {
        return list;
    }

    public void setList(List<LockSlot> list) {
        this.list = list;
    }

    public static class LockSlot {

        private String item;
        private int slot;

        public LockSlot(String item, int slot) {
            this.item = item;
            this.slot = slot;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof LockSlot)) {
                return false;
            }

            LockSlot lockSlot = (LockSlot) obj;

            return lockSlot.getSlot() == this.getSlot() && lockSlot.getItem().equals(lockSlot.getItem());
        }

        public int compareTo(Object o) {

            LockSlot lockSlot = (LockSlot) o;

            if (lockSlot.getSlot() == this.getSlot() && lockSlot.getItem().equals(this.getItem())) {
                return 0;
            } else if (lockSlot.getSlot() == this.getSlot() && !lockSlot.getItem().equals(this.getItem())) {
                return 1;
            }
            return -1;
        }

        @Override
        public String toString() {
            return "Slot: " + getSlot() + " " + "Item: " + getItem();
        }
    }
}
