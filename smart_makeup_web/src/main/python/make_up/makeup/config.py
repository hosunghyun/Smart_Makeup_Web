import argparse
import os


class Config():
    def __init__(self):
        self.parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    def initialize(self):
        self.parser.add_argument('--skin_val', action="store_true", help="init value False, put parameter True")
        # self.parser.add_argument('--lip_val', action="store_true", help="init value False, put parameter True")
        self.parser.add_argument('--skin_root', type=str, default='./makeup_img/skin/')
        self.parser.add_argument('--lip_root', type=str, default="makeup_img/lip/")
        # self.parser.add_argument('--lip_name', type=str, help='lip file name')
        self.parser.add_argument('--skin_name', type=str, help='skin file name')

        self.parser.add_argument('--s_mix', type=int, default = 100, help="value of weight")
        self.parser.add_argument('--l_mix', type=int, default = 90, help="value of weight")

        #-----------------------test
        # self.parser.add_argument('--skin_val', action="store_false", help="init value False, put parameter True")
        self.parser.add_argument('--lip_val', action="store_false", help="init value False, put parameter True")
        # self.parser.add_argument('--skin_name', type=str, default= "extra.jpg", help='skin file name')
        self.parser.add_argument('--lip_name', type=str, default= "extra.jpg", help='skin file name')
        args = self.parser.parse_args()
        return args
