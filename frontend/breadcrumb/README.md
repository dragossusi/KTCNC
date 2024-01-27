Module that renders a breadcrumb

## Usage

```kotlin
// create data
val data = remember {
    BreadCrumbData(
        listOf(
            BreadCrumbItemData("etc", onClick = {}),
            BreadCrumbItemData("ssh", onClick = {}),
            BreadCrumbItemData("sshd_config", onClick = {}),
        )
    )
}

// render composable
BreadcrumbView(data)
```

## Preview

[![screenshot](preview/BreadCrumbView.png)](preview/BreadCrumbView.png)
