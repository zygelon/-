using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;

using System.Threading.Tasks;

namespace Project1
{
    class Class1
    {

        static void SetZero(ref int[,] arr)
        {
            for (int i = 0; i < Num; ++i)
            {
                for (int j = 0; j < Num; ++j)
                    arr[i, j] = 0;
            }
        }

        const int Num = 2000;
        static void Main(string[] args)
        {
            int[,] a = new int[Num, Num];
            int[,] b = new int[Num, Num];
            int[,] c = new int[Num, Num];

            SetZero(ref a);
            SetZero(ref b);
            SetZero(ref c);
            var watch = new System.Diagnostics.Stopwatch();

            watch.Start();

           // int g;
            
            Parallel.For(0, Num, i =>
            {
                for (int g = 0; g < Num; g++)
                {
                    int sum = 0;
                    for (int j = 0; j < Num; j++)
                        sum += a[i, j] * b[j, g];
                    c[i, g] = sum;
                }
            });
            watch.Stop();
            Console.WriteLine("{0}", watch.ElapsedMilliseconds);
            Console.ReadLine();
        }
    }
}
