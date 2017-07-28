package xyz.zimuju.common.widget.swiperefreshlayout;

/*
 * @description SwipeRefreshLayoutDirection : 上拉/下拉手势
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-9-28-上午9:59
 * @version v1.0.0
 */
public enum SwipeRefreshLayoutDirection {
    TOP(0),
    BOTTOM(1),
    BOTH(2);

    private int value;

    SwipeRefreshLayoutDirection(int value) {
        this.value = value;
    }

    public static SwipeRefreshLayoutDirection getFromInt(int value) {
        for (SwipeRefreshLayoutDirection direction : SwipeRefreshLayoutDirection.values()) {
            if (direction.value == value) {
                return direction;
            }
        }
        return BOTH;
    }
}
