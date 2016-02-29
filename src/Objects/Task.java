package Objects;

/**
 * Represents an item and the quantity needed.
 */
public class Task {

	private final Item item;
	private final int qty;
    private final boolean completed;

	/**
	 * Create a job item from an item and a quantity.
	 * @param item The item.
	 * @param qty The quantity needed.
	 */
	public Task(Item item, int qty) {

		this.item = item;
		this.qty = qty;
        this.completed = false;

	}

	/**
	 * Get the item.
	 * @return The item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Get the quantity.
	 * @return The quantity.
	 */
	public int getQuantity() {
		return qty;
	}

    /**
     * Set the task to completed.
     */
    public void completed() {
        completed = true;
    }

	// toString method for debugging purposes
	@Override
	public String toString() {
		return "Task [item=" + item + ", qty=" + qty + "]";
	}
}
