### 解题思路
这样做时间复杂度大概为O(n)

### 代码

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int gg = 0;
        for ( int i = m; i < m + n; i ++){
            nums1[i] = nums2[gg++];    // 将第二个数组逐个放到第一个数组尾端
        }Arrays.sort(nums1, 0, n + m);
    }
}
```