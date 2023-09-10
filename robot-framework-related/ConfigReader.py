import ConfigParser
import re;
import os;


class ConfigReader:

    ROBOT_LIBRARY_SCOPE = 'GLOBAL'

    def __init__(self):
        print 'Inside constructor'
        self.pattern = re.compile(r'(\$\{(.+?)\.(.+?)\})')
        self.config = None
        # self.var_dict = None

    def resolve_value(self, value):
        tmp = value
        for (match, ref_sect, ref_key) in re.findall(self.pattern, value):
            new_value = self.resolve_value(self.config.get(ref_sect, ref_key))

            # next substitute it
            tmp = tmp.replace(match, new_value)
        return tmp

    def load_config(self, path_to_dir):

        full_path = os.path.join(path_to_dir, 'config.ini')

        config = ConfigParser.ConfigParser()
        config.read(full_path)
        self.config = config

        variables = {}
        for section in config.sections():
            for key, value in config.items(section):
                var = "%s.%s" % (section, key)
                new_val = value

                resolved = self.resolve_value(value)
                if resolved != value:
                    config.set(section, key, resolved)
                    new_val = resolved

                variables[var] = new_val
        self.var_dict = variables
        print self.var_dict
        # print variables

    def get_config_value(self, key):
        print 'key is' + key
        print self.pattern
        print self.var_dict

        return self.var_dict[key]


if __name__ == '__main__':
    reader = ConfigReader()
