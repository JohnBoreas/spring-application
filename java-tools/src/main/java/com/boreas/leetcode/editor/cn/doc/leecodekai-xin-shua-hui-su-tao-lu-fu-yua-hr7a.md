咱们继续按照分类来肝leetcode回溯分类下的题目，一直按照同一个分类来刷，比较容易养成自己的规则。

今天的题目是复原 IP 地址
：https://leetcode-cn.com/problems/restore-ip-addresses/

## 题意
给定一个只包含数字的字符串，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。

有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。

例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。

## 思路推演
这道题看起来比较花哨，都说到了IP地址，但是把它庸俗化一下，其实是一道字符串组合的题目。

大意是给你一个字符串，把这个大字符串拆分成小字符串，然后让满足条件的小字符串最终组合起来，输出所有满足条件的组合。

这里有两个需要关注的小规则：
1. 小字符串需要在0-255之间，并且不能存在01，023这种情况。
2. 最终组成的大字符串是给一开始定的字符串，并且是有序的。（总结一下就是字符串拆分，但是为了面对回溯好理解就按照组合来描述了。）
3. 最多只能拆分4份。（因为ip地址最多就是4段嘛）

好了，话说到这个地步了，那我们就按照我们以往的回溯规则开始整吧。
- 终止条件：ip段最多4段，超过4段终止。如果等于4段但是不是一开始完整的字符串也终止。如果4段并且组合起来是一开始的字符串则添加到结果里面去。
- 过滤条件：如果字符串在0-255之外，过滤。如果字符串存在01这种情况，过滤。
- 经典选择回退：先选择这个字符串，演算完所有相关情况，最后回退选择。

把上面的套路代码填入代码即可。

```
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        dfs(s, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(String s, int startIndex, List<String> path, List<String> res) {
        // 终止条件：ip段最多4段，超过4段终止。如果等于4段但是不是一开始完整的字符串也终止。如果4段并且组合起来是一开始的字符串则添加到结果里面去。
        if(path.size() > 4) {
            return;
        }
        if(path.size() >= 4 && startIndex != s.length()) {
            return;
        }

        if(path.size() == 4) {
            res.add(String.join(".", path));
            return;
        }

        for(int i = startIndex; i < s.length(); i++) {
            // 过滤条件：如果字符串在0-255之外，过滤。如果字符串存在01这种情况，过滤。
            String newStr = s.substring(startIndex, i + 1);
            if((newStr.length() > 1 && newStr.startsWith("0")) || newStr.length() > 3) {
                continue;
            }
            int value = Integer.valueOf(newStr);
            if(value < 0 || value > 255) {
                continue;
            }
            //先选择这个字符串，演算完所有相关情况，最后回退选择。
            path.add(newStr);
            dfs(s, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }
}
```

## 最后
打完收工。明天继续怼回溯的题目17.电话号码的字母组合：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/

欢迎关注公众号【理解并背诵君】