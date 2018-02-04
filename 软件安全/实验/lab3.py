# coding=utf-8

class LCS():
    def input(self, x, y):
    #读入待匹配的两个字符串
        if type(x) != str or type(y) != str:
            print 'input error'
            return None
        self.x = x
        self.y = y

    def Compute_LCS(self):
        xlength = len(self.x)
        ylength = len(self.y)
        self.direction_list = [None] * xlength #这个二维列表存着回溯方向
        for i in xrange(xlength):
            self.direction_list[i] = [None] * ylength
        self.lcslength_list = [None] * (xlength + 1)      
        #这个二维列表存着当前最长公共子序列长度
        for j in xrange(xlength + 1):
            self.lcslength_list[j] = [None] * (ylength + 1)

        for i in xrange(0, xlength + 1):
            self.lcslength_list[i][0] = 0
        for j in xrange(0, ylength + 1):
            self.lcslength_list[0][j] = 0
        #下面是进行回溯方向和长度表的赋值
        for i in xrange(1, xlength + 1):
            for j in xrange(1, ylength + 1):
                if self.x[i - 1] == self.y[j - 1]:
                    self.lcslength_list[i][j] = self.lcslength_list[i - 1][j - 1] + 1
                    self.direction_list[i - 1][j - 1] = 0  # 左上
                elif self.lcslength_list[i - 1][j] > self.lcslength_list[i][j - 1]:
                    self.lcslength_list[i][j] = self.lcslength_list[i - 1][j]
                    self.direction_list[i - 1][j - 1] = 1  # 上
                elif self.lcslength_list[i - 1][j] < self.lcslength_list[i][j - 1]:
                    self.lcslength_list[i][j] = self.lcslength_list[i][j - 1]
                    self.direction_list[i - 1][j - 1] = -1  # 左
                else:
                    self.lcslength_list[i][j] = self.lcslength_list[i - 1][j]
                    self.direction_list[i - 1][j - 1] = 2  # 左或上
        self.lcslength = self.lcslength_list[-1][-1]
        return self.direction_list, self.lcslength_list

    def printLCS(self, curlen, i, j, s):
        if i == 0 or j == 0:
            return None

        if self.direction_list[i - 1][j - 1] == 0:
            if curlen == self.lcslength:
                s += self.x[i - 1]
                for i in range(len(s)-1,-1,-1):
                    print s[i],
                print '\n'
            elif curlen < self.lcslength:
                s += self.x[i-1]
                self.printLCS(curlen + 1, i - 1, j - 1, s)
        elif self.direction_list[i - 1][j - 1] == 1:
            self.printLCS(curlen,i - 1, j,s)
        elif self.direction_list[i - 1][j - 1] == -1:
            self.printLCS(curlen,i, j - 1,s)
        else:
            self.printLCS(curlen,i - 1, j,s)
            self.printLCS(curlen,i, j - 1,s)


    def returnLCS(self):
        #回溯的入口
        self.printLCS(1,len(self.x), len(self.y),'')


if __name__ == '__main__':
    p = LCS()
    p.input('abcbdab', 'bdcaba')
    p.Compute_LCS()
    p.returnLCS()