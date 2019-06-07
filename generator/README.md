# OASP Android Generator

OASP Android Generator is a simple, Yeoman-based, project scaffolding utility, which helps to kickstart your native Android project based on OASP Android Template.

# Installation

OASP-AG requires [Node.js](https://nodejs.org/) and [npm](https://npmjs.org). If you already have it, the only thing you will have to do is to install the utility itself. Please follow below instructions.
Install Yeoman using npm. Yeoman is needed to properly understand and run the generator.
```
npm install -g yo
```
Install the OASP-AG module from its repository using npm. This will install the generator itself and all the related modules. *NOTE*: you need to run this command in GIT Bash.
```
npm install git+https://github.com/devonfw-forge/devon4android/generator
```
  
# Usage 

After you have installed the utility, navigate to directory in which you would like to have your project created. Then just type:

```
yo oasp-android
```
It will run the project scaffolding wizard. The utility will guide you through the scaffolding process.

# TODOs

- Currently the Android app template code is merged with the generator. It would be best solution, to dynamically load the app code from its repository while running the generator and create the new project based on the downloaded code. This would ensure that the generator always uses current template.
- Additional features can be added, for example: adding useful libraries git nto the generated project