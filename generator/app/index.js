
{
  // TODO: download app template from git
  // TODO: add option to push newly generated template to git
  // TODO: add options to add specific libs

  const yeoman = require('yeoman-generator');
  const yosay = require('yosay');
  const chalk = require('chalk');

  const msgAppNameError = 'Your application name cannot contain special characters or a blank space: ';
  const msgPackageNameError = 'The package name you have provided is not a valid Java package name: ';
  const msgSdkError = 'The SDK number you have provided is wrong.';

  const compileSdkVersion = /(compileSdkVersion)( )([0-9])*([0-9])/;
  const minSdkVersion = /(minSdkVersion)( )([0-9])*([0-9])/;
  const targetSdkVersion = /(targetSdkVersion)( )([0-9])*([0-9])/;
  const applicationId = /(applicationId)( )([^\n]+)/;

  const packageNameRegex = /^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/;
  const projectNameRegex = /^([a-zA-Z0-9_]*)$/;
  const sdkNumberRegex = /^[1-9]?\d$/;
  const escapeRegex = /[-/\\^$*+?.()|[\]{}]/g;

  var basePackageName;
  var basePackagePath;

  module.exports = yeoman.extend(
    {

      initializing: function () {
        var props;
        var dirs;

        this.log(yosay('Welcome to ' + chalk.bold.bgYellow('OASP Android') + ' project template generator. Please follow the instructions.'));
      },

      default: function () {

        // ### HELPER METHODS

        function escapeRegExp(str) {
          return str.replace(escapeRegex, '\\$&');
        };

        function recognizeCurrentPackage(recognizedPackage) {
          recognizedPackage = recognizedPackage.replace(/(\r\n|\n|\r|")/gm, '');
          basePackageName = new RegExp(escapeRegExp(recognizedPackage), 'g');
          dirs.basePackagePath = recognizedPackage.replace(/\./g, '/');
        };

        // ### TEMPLATING METHODS

        this.copyFiles = function (from, to) {
          this.fs.copy(
            this.templatePath(from),
            this.destinationPath(to)
          );
        };

        this.copyAndReplace = function (from, to, replaceWhat, replaceTo) {
          this.fs.copy(
            this.templatePath(from),
            this.destinationPath(to), {
              process: function (content) {
                return content.toString().replace(replaceWhat, replaceTo);
              }
            }
          );
        };

        this.copyGradle = function (inProjectDir) {
          this.fs.copy(
            this.templatePath('app/build.gradle'),
            this.destinationPath(inProjectDir + '/build.gradle'), {
              process: function (content) {
                packageMatch = content.toString().match(applicationId);
                if (packageMatch) {
                  recognizeCurrentPackage(packageMatch[0].split(' ')[1]);
                }
                var newContent = content.toString();
                newContent = newContent.replace(targetSdkVersion, 'targetSdkVersion ' + props.targetSdk);
                newContent = newContent.replace(minSdkVersion, 'minSdkVersion ' + props.minSdk);
                newContent = newContent.replace(compileSdkVersion, 'compileSdkVersion ' + props.compileSdk);
                newContent = newContent.replace(applicationId, 'applicationId "' + props.packageName + '"');
                return newContent;
              }
            }
          );
        };

        // ### MAIN TASKS METHODS

        this.baseTemplating = function () {

          const resDir = dirs.mainDir + '/res';

          // copies files from main folder
          this.copyFiles('.*', props.projectName);
          this.copyFiles('*.*', props.projectName);

          // copies gradle related file
          this.copyFiles('gradlew', props.projectName + '/gradlew');
          this.copyFiles('gradle/', props.projectName + '/gradle');

          // copies files from 'app' module
          this.copyFiles('app/!(build.gradle)', dirs.projectDir);
          this.copyFiles('app/.*', dirs.projectDir);

          // copies app resources
          this.copyFiles('app/src/main/res', resDir);
        };

        this.templateCode = function () {

          const destAndroidTestDir = dirs.srcDir + '/androidTest/' + dirs.packageDir;
          const destTestDir = dirs.srcDir + '/test/' + dirs.packageDir;

          // templates app code
          var currentCodePath = 'app/src/main/java/' + dirs.basePackagePath;
          this.copyAndReplace(currentCodePath, dirs.codeDir, basePackageName, props.packageName);

          // templates androidTest dir
          var androidTestDir = 'app/src/androidTest/java/' + dirs.basePackagePath;
          this.copyAndReplace(androidTestDir, destAndroidTestDir, basePackageName, props.packageName);

          // templates test dir
          var testDir = 'app/src/test/java/' + dirs.basePackagePath;
          this.copyAndReplace(testDir, destTestDir, basePackageName, props.packageName);
        };

        this.templateConfig = function () {

          // templates 'app' module build.gradle
          this.copyGradle(dirs.projectDir);

          // templates manifest
          this.copyAndReplace('app/src/main/AndroidManifest.xml', dirs.mainDir + '/AndroidManifest.xml', basePackageName, props.packageName);
        };

      },

      prompting: function () {
        return this.prompt([{
          type: 'input',
          name: 'projectName',
          message: 'New project name:',
          default: this.appname,
          validate: function (input) {
            if (projectNameRegex.test(input)) {
              return true;
            }
            return chalk.bgRed(msgAppNameError + input);
          }
        },
        {
          type: 'input',
          name: 'packageName',
          message: 'Project package name:',
          validate: function (input) {
            if (packageNameRegex.test(input)) {
              return true;
            }
            return chalk.bgRed(msgPackageNameError + input);
          }
        },
        {
          type: 'input',
          name: 'compileSdk',
          message: 'Compile Android SDK:',
          default: 25,
          validate: function (input) {
            if (sdkNumberRegex.test(input)) {
              return true;
            }
            return chalk.bgRed(msgSdkError);
          }
        },
        {
          type: 'input',
          name: 'targetSdk',
          message: 'Target Android SDK:',
          default: 25,
          validate: function (input) {
            if (sdkNumberRegex.test(input)) {
              return true;
            }
            return chalk.bgRed(msgSdkError);
          }
        },
        {
          type: 'input',
          name: 'minSdk',
          message: 'Minimum Android SDK:',
          default: 19,
          validate: function (input) {
            if (sdkNumberRegex.test(input)) {
              return true;
            }
            return chalk.bgRed(msgSdkError);
          }
        }]).then((answers) => {
          props = answers;
        });
      },

      writing: function () {

        dirs = {};
        dirs.packageDir = props.packageName.replace(/\./g, '/');
        dirs.srcDir = props.projectName + '/app/src';
        dirs.projectDir = props.projectName + '/app';
        dirs.mainDir = dirs.srcDir + '/main';
        dirs.codeDir = dirs.mainDir + '/java/' + dirs.packageDir;

        this.baseTemplating();
        this.templateConfig();
        this.templateCode();

      },

      end: function () {
        this.log(yosay('Your project ' + chalk.bold.bgGreen(props.projectName) 
        + ' has been succesfully created! You can find it in: '
        + chalk.bold.bgGreen(this.destinationPath(props.projectName))));
      }

    });
}