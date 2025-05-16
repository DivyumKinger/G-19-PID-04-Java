class BookLinkedList {
    private BookNode head;

    public void add(Book data) {
        BookNode newNode = new BookNode(data);
        if (head == null) {
            head = newNode;
        } else {
            BookNode curr = head;
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
        BookNode curr = head;
        while (curr.next != null) {
            BookNode nextNode = curr.next;
            if (nextNode.data.getId() == id) {
                curr.next = nextNode.next;
                return true;
            }
            curr = nextNode;
        }
        return false;
    }

    public Book findById(int id) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.data.getId() == id)
                return curr.data;
            curr = curr.next;
        }
        return null;
    }

    // public void forEach(java.util.function.Consumer<Book> action) {
    //     BookNode curr = head;
    //     while (curr != null) {
    //         action.accept(curr.data);
    //         curr = curr.next;
    //     }
    // }

    BookNode getHead() {
        return head;
    }
}