import { useRouter, useRoute } from "vue-router";

export function useAuthRedirect() {
  const router = useRouter();
  const route = useRoute();

  const redirectAfterAuth = () => {
    const redirectPath = route.query.redirect || "/";
    router.push(redirectPath);
  };

  return {
    redirectAfterAuth,
  };
}
