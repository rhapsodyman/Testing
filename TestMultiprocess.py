from multiprocessing.pool import ThreadPool
import os, time
from multiprocessing import Pool
import os, time

from subprocess import Popen, PIPE
import sys
import os
import signal


#
# print("hi outside of main()")
#
# def hello(x):
#     print("inside hello()")
#     print("Proccess id: ", os.getpid())
#     time.sleep(3)
#     return x*x
#
# if __name__ == "__main__":
#     p = ThreadPool(5)
#     pool_output = p.map(hello, range(3))
#
#     print(pool_output)




print("hi outside of main()")

def get_time_ms():
    millis = int(round(time.time() * 1000))
    return millis

def hello(x):
    # print("inside hello()")
    ms = str(get_time_ms())
    print("Proccess id: {} ms {}".format(os.getpid(), get_time_ms()))
    time.sleep(3)
    return x*x



def running_popen(non_used):

    command = 'ping 8.8.8.8'
    process = Popen(command, shell=True, stdout=PIPE, stderr=PIPE)

    print("Proccess id: {} ms {}".format(process.pid, get_time_ms()))

    stdout, stderr = process.communicate()
    # print(stdout)

    print('waiting to terminate')
    process.wait()



if __name__ == "__main__":
    p = Pool(20)
    pool_output = p.map(hello, range(20)) # takes ~ 276 ms - to start 20 processes
    print(pool_output)

    # p = ThreadPool(20)
    # pool_output = p.map(running_popen, range(20)) # takes ~ 40 ms - to start 20 processes
    # print('done')









