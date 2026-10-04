package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.littlelemon.data.MenuItemDao
import com.example.littlelemon.data.MenuItemRoom
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Home(dao: MenuItemDao, onProfileClick: () -> Unit) {
    val menuItems by remember(dao) { dao.getAll() }.collectAsStateWithLifecycle(initialValue = emptyList())
    var searchPhrase by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    val categories = menuItems.map { it.category }.distinct()
    val filtered = menuItems
        .filter { selectedCategory == null || it.category == selectedCategory }
        .filter { searchPhrase.isBlank() || it.title.contains(searchPhrase, ignoreCase = true) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding(),
    ) {
        Header(onProfileClick = onProfileClick)
        LazyColumn(Modifier.fillMaxSize()) {
            item { Hero(searchPhrase, onSearchChange = { searchPhrase = it }) }
            item {
                MenuBreakdown(
                    categories = categories,
                    selected = selectedCategory,
                    onSelect = { selectedCategory = if (selectedCategory == it) null else it },
                )
            }
            if (menuItems.isEmpty()) {
                item {
                    Text(
                        "Loading menu…",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray,
                    )
                }
            }
            items(filtered, key = { it.id }) { item ->
                MenuItemRow(item)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp), color = LittleLemonColor.cloud)
            }
        }
    }
}

@Composable
private fun Hero(searchPhrase: String, onSearchChange: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green)
            .padding(16.dp),
    ) {
        Text(
            "Little Lemon",
            color = LittleLemonColor.yellow,
            fontFamily = FontFamily.Serif,
            fontSize = 40.sp,
            fontWeight = FontWeight.Medium,
        )
        Row(verticalAlignment = Alignment.Top) {
            Column(Modifier.weight(1f)) {
                Text("Chicago", color = Color.White, fontFamily = FontFamily.Serif, fontSize = 26.sp)
                Spacer(Modifier.height(12.dp))
                Text(stringResource(R.string.hero_description), color = Color.White, fontSize = 15.sp)
            }
            Spacer(Modifier.width(12.dp))
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "Bruschetta served at Little Lemon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(width = 130.dp, height = 140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
        Spacer(Modifier.height(16.dp))
        TextField(
            value = searchPhrase,
            onValueChange = onSearchChange,
            placeholder = { Text("Enter search phrase") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LittleLemonColor.cloud,
                unfocusedContainerColor = LittleLemonColor.cloud,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun MenuBreakdown(categories: List<String>, selected: String?, onSelect: (String) -> Unit) {
    Column(Modifier.padding(vertical = 16.dp)) {
        Text(
            "ORDER FOR DELIVERY!",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selected,
                    onClick = { onSelect(category) },
                    label = { Text(category.replaceFirstChar { it.uppercase() }, fontWeight = FontWeight.Bold) },
                    shape = RoundedCornerShape(16.dp),
                    border = null,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = LittleLemonColor.cloud,
                        labelColor = LittleLemonColor.green,
                        selectedContainerColor = LittleLemonColor.green,
                        selectedLabelColor = Color.White,
                    ),
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(Modifier.padding(horizontal = 16.dp), color = LittleLemonColor.cloud)
    }
}

@Composable
private fun MenuItemRow(item: MenuItemRoom) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                item.description,
                color = LittleLemonColor.green,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(6.dp))
            Text("$%.2f".format(item.price), fontWeight = FontWeight.SemiBold, color = LittleLemonColor.charcoal)
        }
        Spacer(Modifier.width(12.dp))
        AsyncImage(
            model = item.image,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LittleLemonColor.cloud),
        )
    }
}
