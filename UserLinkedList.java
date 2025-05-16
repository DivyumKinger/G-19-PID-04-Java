class UserLinkedList {
    private UserNode head;

    public void add(User data) {
        UserNode newNode = new UserNode(data);
        if (head == null) {
            head = newNode;
        } else {
            UserNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
    }

    public boolean removeById(int id) {
        if (head == null)
            return false;
        if (head.data.getId() == id) {
            head = head.next;
            return true;
        }
        UserNode curr = head;
        while (curr.next != null) {
            UserNode nextNode = curr.next;
            if (nextNode.data.getId() == id) {
                curr.next = nextNode.next;
                return true;
            }
            curr = nextNode;
        }
        return false;
    }

    public User findById(int id) {
        UserNode curr = head;
        while (curr != null) {
            if (curr.data.getId() == id)
                return curr.data;
            curr = curr.next;
        }
        return null;
    }

    // public void forEach(java.util.function.Consumer<User> action) {
    //     UserNode curr = head;
    //     while (curr != null) {
    //         action.accept(curr.data);
    //         curr = curr.next;
    //     }
    // }

    UserNode getHead() {
        return head;
    }
}
