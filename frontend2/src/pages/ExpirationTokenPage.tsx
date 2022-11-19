import { Typography, Stack, Box} from "@mui/material";
import EmojiIcon from "@mui/icons-material/SentimentDissatisfied";

function ExpirationTokenPage() {
  return (
    <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" height="100vh">
      <Stack direction="column">
        <EmojiIcon style={{"fontSize": "200px"}}/>

      <Typography variant="h5" component="h5">
        토큰이 만료됐습니다
      </Typography>
    </Stack>
    </Box>
  );
}

export default ExpirationTokenPage;
