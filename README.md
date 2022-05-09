# Titanium Android Tabbed Bar

Use the native `MaterialButtonToggleGroup` API for modern tabbed bars in Titanium.

<img src="./example.png" height="70" />

## Requirements

- [x] Titanium SDK 9.3.0+ (because it's a `material-components` API)

## Example

```js
const tabbedBar = require('ti.tabbedbar').createTabbedBar({
  labels: [ 'Option 1', 'Option 2', 'Option 3' ],
  index: 1
});
```

## License

MIT

## Author

Hans Knöchel
