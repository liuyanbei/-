#include<stdio.h>
int sum(int num[],int n) {
    if (sizeof(num) == 0) {
        return 0;
    }
    int sum = 0;
    int max = 0;
        for (int i = 0; i < n; i++) {
            if (sum <= 0) {
                sum = num[i];
            }
            else {
                sum = sum + num[i];
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
}

int main()
{
    int i, n;
    scanf_s("%d", &n);
    int num[100];
    for (i = 0; i < n; i++)
        scanf_s("%d", &num[i]); 
    int max = sum(num,n);
    printf("连续子元素的最大和为：%d",max);
}

