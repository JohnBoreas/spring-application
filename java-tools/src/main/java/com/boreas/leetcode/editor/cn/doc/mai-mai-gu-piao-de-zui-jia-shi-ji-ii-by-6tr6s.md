# 思路

要求尽可能地完成更多的交易，计算所能获得的最大利润。
每天买卖结束以后要么持有，要么不持有，可以设置两个状态量来表示每天持有和非持有的状态。
假设`dp[i][0]`表示第i天结束后**持有**股票时获得的最大利润，那么有两种情况：
* 第i-1天就持有股票（`dp[i-1][0]`）；
* 第i-1天不持有股票，并买入了第i天的股票(`dp[i-1][1] - prices[i]`，这里因为是买入股票，利润是负的)。

假设`dp[i][1]`表示第i天结束后**不持有**股票时获得的最大利润，那么有两种情况：
* 第i-1天就不持有股票（`dp[i-1][1]`）；
* 第i-1天持有股票，并在第i天卖出了股票(`dp[i-1][0] + prices[i]`，这里因为是卖出股票，利润是正的)。

以上两种状态，对于两种情况分别取最大值即可。

**初始化状态：**
* `dp[0][0] = -prices[0]`：第0天结束持有股票，只能是买入了第0天的股票，注意买入是负值。
* `dp[0][1] = 0`：第0天结束不持有股票，利润为0。

**结束条件**
* `dp[n-1][1]`：表示最后一天并且不持有股票时的最大利润。

# 代码
```java
class Solution {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int[][] dp = new int[len][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for(int i = 1; i < len; i ++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i-1][0] + prices[i], dp[i-1][1]);
        }
        return dp[len-1][1];
    }
}
```

# 优化代码
既然每天的状态都只能由上一天的状态转换而来，那么可以使用两个状态量代替状态数组。
```java
class Solution {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int dp0 = -prices[0], dp1 = 0;
        for(int i = 1; i < len; i ++) {
            int dp0Tmp = dp0;
            dp0 = Math.max(dp0, dp1 - prices[i]);
            dp1 = Math.max(dp0Tmp + prices[i], dp1);
        }
        return dp1;
    }
}
```



