// Time Complexity : O(1)
// Space Complexity : O(n) n is the capacity
// Did this code successfully run on Leetcode : Yes

// Time Complexity : O(N) is all are nested in worst case(Like a skewed tree)
// Space Complexity : O(H) height of nestings
// Did this code successfully run on Leetcode : Yes

/** Notes

LRU cache is least recently used cache. We're given a few key value pairs
to put in the cache. the pair that is put first in the cache will be Least
recently used, and the pair which is put last is MRU ie most recently used We have to implement 2 functions
1) Get : get the value for the given key, and since the pair is now accessed (to get the value), it will be moved to MRU place.
2a. Put Existing: put the values for a given key. there would be 2 cases for put either it would be an existing node ie the key value pair already 
        exists, in that case update the value for the given key and       move  it to MRU or it would be a new now. 
2b. Fresh (New) node: here we can have 2 cases either the capacity is full
or the capacity is not full, if the capacity is not full, I'll add the key-value pair and make it MRU.
If the capacity is full, delete the least recently used pair, add the new key-value pair and make it MRU

** Order is maintained in the LL. get operation does not use the map
** To make it MRU we have to more the element to the head of the list
** HashMap has reference to the LL nodes, so both get updated at the same time 


 */

 class LRUCache {
    class Node{
        int key;
        int val;
        Node next; 
        Node prev;
        public Node(int key, int val){
            this.key = key;
            this.val = val;
        }
    }

    private void addToHead(Node node){
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }

    private void removeNode(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private Node head;
    private Node tail;
    private int capacity;
    private HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.head = new Node(-1,-1);
        this.tail = new Node(-1,-1);
        this.head.next = this.tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;
        Node node = map.get(key);
        removeNode(node);
        addToHead(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        //existing
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            removeNode(node);
            addToHead(node);
        }else{
            //fresh
            if(capacity == map.size()){ //full
                Node tailPrev = tail.prev;
                removeNode(tailPrev);
                map.remove(tailPrev.key);
            }
            Node newNode = new Node(key,value);
            addToHead(newNode);
            map.put(key, newNode);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */